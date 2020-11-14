import {IUser} from "./user";

export interface IPost {
    content: string;
    createdAt: string;
    author: IUser;
}

export interface IPostFormValues {
    content: string;
}