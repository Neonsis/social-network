import {IUser} from "./user";

export interface IPost {
    id: string;
    content: string;
    createdAt: string;
    author: IUser;
    isLiked: boolean;
    countLike: number;
    comments: IComment[];
}

export interface IPostFormValues {
    content: string;
}

export interface IComment {
    id: string;
    content: string;
    postId: string;
    user: IUser;
    createdAt: string;
}

export interface ICommentFormValues {
    content: string;
}
