export interface Post {
  userId?:number;
  id?:number;
  title?:string;
  body?:string;
  category?:string;
  liked?: boolean;
}