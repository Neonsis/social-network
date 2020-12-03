import {IPhoto, IUser} from "./user";

export interface IGroup {
    id: string;
    title: string;
    moderator: IUser;
    avatar: IPhoto;
    followersCount: number;
}

export interface IGroupDetails extends IGroup {
    isUserFollow: boolean;
}