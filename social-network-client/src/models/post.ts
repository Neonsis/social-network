import {IUser} from "./user";
import {IGroup} from "./groups";

export interface IPost {
    id: string;
    content: string;
    createdAt: string;
    author?: IUser;
    community?: IGroup;
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
    profile: IUser;
    createdAt: string;
}

export interface ICommentFormValues {
    content: string;
}
