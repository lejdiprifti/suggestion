import { Injectable } from '@angular/core';
import { ApiService } from '@ikubinfo/core/utilities/api.service';
import { Observable } from 'rxjs';
import { Category } from '../models/category';

@Injectable()
export class CategoryService {

    url = 'categories';

    constructor(private apiService: ApiService) {

    }

    private async convertToCategory(categoryRaw: any): Promise<Category> {
        let category: Category = {
            categoryName: categoryRaw.categoryName,
            categoryDescription: categoryRaw.categoryDescription,
            userId: categoryRaw.user && categoryRaw.user.id || 0 
        }
        return category;
    }

    public async convertToCategories(categoryRaw: any): Promise<Array<Category>> {
        let categories: Array<Category> = [];
        categoryRaw.forEach(async element => {
            categories.push(await this.convertToCategory(element));
        });
        return categories;
    }

    public  allCategories = async (): Promise<Array<Category>> => {
       return this.convertToCategories(this.apiService.get<Array<any>>(this.url).toPromise());
    }


    // public editAsync = (
    //     id: number,
    //     post: Post
    // ): Observable<Post> => {
    //     const url = this.url + `/${id}`;
    //     return this.apiService.put<Post>(url, post);
    // }

    public createCategory = (
        category: Category,
    ): Promise<Category> => {
        return this.convertToCategory( this.apiService.post<any>(this.url, category).toPromise());
    }


    public deleteAsync = (id: number): Observable<void> => {
        const url = this.url + `/${id}`;
        return this.apiService.delete(url);
    }
}