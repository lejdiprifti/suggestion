import { User } from './user';
import { Post } from './post';

export interface Comment{
    id? : number;
    description?: string;
    user?: User;
    post?: Post;
    addedDate?: Date;
    flag?: boolean;
}