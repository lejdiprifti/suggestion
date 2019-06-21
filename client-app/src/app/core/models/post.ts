import { Category } from './category';
import { User } from './user';

export interface Post {
  postId?:number;
  postName?: string;
  postDescription?: string;
  addedDate?: Date;
  user?: User;
  category?: Category;
}