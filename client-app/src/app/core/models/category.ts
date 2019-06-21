import { User } from './user';
import { State } from './state.enum';

export interface Category {
    categoryId?: number;
    categoryName?: string;
    categoryDescription?: string;
    proposedUser?: User;
    categoryState?: State;
    acceptedUser?: User;
    acceptedDate?: Date;
}