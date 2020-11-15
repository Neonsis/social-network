import {IUser} from "./user";

export interface IPost {
    id: string;
    content: string;
    createdAt: string;
    author: IUser;
    isLiked: boolean;
    countLike: number;
}

export interface IPostFormValues {
    content: string;
}