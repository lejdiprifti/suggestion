import { Category } from './category';
import { User } from './user';

export interface Post {
  userId?:number;
  id?:number;
  title?:string;
  body?:string;
  isLiked?:boolean;
  postId?:number;
  postName?: string;
  postDescription?: string;
  addedDate?: Date;
  user?: User;
  category?: Category;
}