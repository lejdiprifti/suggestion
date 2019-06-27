
import { User } from './user';
import { Category } from './category';

export interface Post {
  userId?:number;
  id?:number;
  title?:string;
  body?:string;
  postId?:number;
  postName?: string;
  postDescription?: string;
  addedDate?: Date;
  user?: User;
  category?: Category;
  isLiked?:boolean;
}