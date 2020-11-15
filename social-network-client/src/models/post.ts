import {IUser} from "./user";

export interface IPost {
    id: string;
    content: string;
    createdAt: string;
    author: IUser;
}

export interface IPostFormValues {
    content: string;
}