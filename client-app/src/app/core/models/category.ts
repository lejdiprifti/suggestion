import { User } from './user';
import { State } from './state.enum';

export interface Category {
    categoryId?: number;
    categoryName?: string;
    categoryDescription?: string;
    user?: User;
    categoryState?: State;
    acceptedUser?: User;
    acceptedDate?: Date;
    isSubscribed?: boolean;
}