
import { User } from './user';
import { Category } from './category';

export interface Post {
  postId?:number;
  postName?: string;
  postDescription?: string;
  addedDate?: Date;
  user?: User;
  category?: Category;
  isLiked?:boolean;
  categoryId?: number;
  image?: String;
  link?: String;
}