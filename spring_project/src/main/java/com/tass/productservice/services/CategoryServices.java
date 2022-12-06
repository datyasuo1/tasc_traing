package com.tass.productservice.services;

import com.tass.productservice.databases.entities.Category;
import com.tass.productservice.databases.entities.CategoryRelationship;
import com.tass.productservice.databases.repository.CategoryRelationshipRepository;
import com.tass.productservice.databases.repository.CategoryRepository;
import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.ERROR;
import com.tass.productservice.model.dto.SearchBody;
import com.tass.productservice.model.request.CategoryRequest;
import com.tass.productservice.model.response.SearchCategoryResponse;
import com.tass.productservice.utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CategoryServices {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryRelationshipRepository categoryRelationshipRepository;

    public SearchCategoryResponse search(Integer isRoot , String name, Integer page, Integer pageSize){
        if (page == null || page < 1){
            page = 1;
        }
        if (pageSize == null || pageSize < 1){
            pageSize = 10;
        }

        SearchCategoryResponse.Data data = new SearchCategoryResponse.Data();
        data.setCurrentPage(page);
        data.setPageSize(pageSize);

        categoryRepository.searchCategory(isRoot , name, page, pageSize, data);

        SearchCategoryResponse response = new SearchCategoryResponse();
        response.setData(data);
        return response;
    }
    @Transactional
    public BaseResponse createCategory(CategoryRequest request) throws ApiException {

        // step  1 : validate request
        validateRequestCreateException(request);

        if (!request.checkIsRoot()){
            // validate parent  ton tai khong

            Optional<Category> checkParentOpt = categoryRepository.findById(request.getParentId());

            if (!checkParentOpt.isPresent()){
                throw new ApiException(ERROR.INVALID_PARAM , "parent is invalid");
            }
        }

        Category category = new Category();
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setName(request.getName());
        category.setIsRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF);

        categoryRepository.save(category);

        if (!request.checkIsRoot()){
            // tao quan he
            CategoryRelationship categoryRelationship = new CategoryRelationship();
            CategoryRelationship.PK pk =new CategoryRelationship.PK(request.getParentId(), category.getId());
            categoryRelationship.setPk(pk);
            categoryRelationshipRepository.save(categoryRelationship);
        }


        return new BaseResponse(200,"success",category);
    }

    @Transactional
    public BaseResponse updateCategory(CategoryRequest request,Long id)throws ApiException{
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        validateRequestCreateException(request);
        if (!optionalCategory.isPresent()){
            throw new ApiException(ERROR.INVALID_PARAM,"category invalid");
        }
        Category existCate = optionalCategory.get();
        existCate.setName(request.getName());
        existCate.setIcon(request.getIcon());
        existCate.setDescription(request.getDescription());
        existCate.setIsRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF);
        if (!existCate.checkIsRoot() && request.getParentId() != null) {

            // 3.2 validate relationship

            CategoryRelationship.PK pk =
                    new CategoryRelationship.PK(request.getParentId(), existCate.getId());

            Optional<CategoryRelationship> categoryRelationshipOptional =
                    categoryRelationshipRepository.findById(pk);

            if (!categoryRelationshipOptional.isPresent()){

                CategoryRelationship categoryRelationship = new CategoryRelationship();
                categoryRelationship.setPk(pk);

                categoryRelationshipRepository.save(categoryRelationship);
            }
        }
        categoryRepository.save(existCate);
        return new BaseResponse(200,"success",existCate);
    }

    private void validateRequestCreateException(CategoryRequest request) throws ApiException{

        if (StringUtils.isBlank(request.getIcon())){
            throw new ApiException(ERROR.INVALID_PARAM , "icon is blank");
        }

        if (StringUtils.isBlank(request.getName())){
            throw new ApiException(ERROR.INVALID_PARAM , "name is banl");
        }

        if (StringUtils.isBlank(request.getDescription())){
            throw new ApiException(ERROR.INVALID_PARAM , "description is Blank");
        }

        if (request.checkIsRoot() && request.getParentId() != null){
            throw new ApiException(ERROR.INVALID_PARAM , "level is invalid");
        }

        if (!request.checkIsRoot() && request.getParentId() == null){
            throw new ApiException(ERROR.INVALID_PARAM , "parent is blank");
        }
    }
    public BaseResponse deleteCategory(Long id)throws ApiException{
        //1:delete category
        categoryRepository.deleteById(id);
        //b2 delete category child
        this.deleteCategoryChild(id);
        return new BaseResponse();
    }
    private void deleteCategoryChild(long id) throws ApiException{
        List<CategoryRelationship> listCateChild = categoryRelationshipRepository.findAllChildrenCateByParentCateId(id);
        if (CollectionUtils.isEmpty(listCateChild)){
            return;
        }
        List<CategoryRelationship> deleteRelationship = new ArrayList<>();
        for (CategoryRelationship cr: listCateChild){
            long countParent = categoryRelationshipRepository.countParent(cr.getPk().getChildrenId());
            if (countParent == 1){
                deleteRelationship.add(cr);
                this.deleteCategoryChild(cr.getPk().getChildrenId());
            }
        }
        if (!CollectionUtils.isEmpty(deleteRelationship)){
            categoryRelationshipRepository.deleteAll(deleteRelationship);
        }
    }

    public Page<Category> searchAll(Specification<Category> specification, int page, int limit)throws ApiException{
        return categoryRepository.findAll(specification, PageRequest.of(page, limit));
    }
    public BaseResponse findAllChild(String name) throws ApiException{
        List<Category> list = categoryRepository.findByName(name);
        if (list.isEmpty()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }
        String query =
                "SELECT c.id, c.name,c.icon,c.description,c.is_root\n" +
                "FROM category_relationship cr join category c\n" +
                "                                   on cr.link_id = c.id\n" +
                "WHERE  EXISTS ( SELECT NULL\n" +
                "                from category c where cr.id = c.id and c.name like '"+name+"' )\n";
        return new BaseResponse(200 , "success", categoryRepository.findAll(query));
    }

    public BaseResponse findAllParent(String name) throws ApiException{
        List<Category> list = categoryRepository.findByName(name);
        if (list.isEmpty()){
            throw new ApiException(ERROR.INVALID_PARAM , "category does not exist!");
        }
        String query =
                "SELECT c.id, c.name,c.icon,c.description,c.is_root\n" +
                        "FROM category_relationship cr join category c\n" +
                        "                                   on cr.id = c.id\n" +
                        "WHERE  EXISTS ( SELECT NULL\n" +
                        "                from category c where cr.link_id = c.id and c.name like '"+name+"' )";
        return new BaseResponse(200 , "success", categoryRepository.findAll(query));
    }
}
