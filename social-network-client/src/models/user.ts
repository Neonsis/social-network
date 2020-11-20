export interface IUser {
    id: string;
    firstName: string;
    lastName: string;
    avatarUrl?: string;
}

export interface IUserAuth extends IUser{
    token: string;
}

export interface IUserDetails {
    id: string;
    firstName: string;
    lastName: string;
    avatarUrl: string;
    email: string;
    isFriend: boolean;
    isLoggedInUser: boolean;
    isFollower: boolean;
    isPendingFriendship: boolean;
}

export interface IUserFormValues {
    email: string;
    password: string;
    gender: "MALE" | "FEMALE";
    birthday: Date;
    firstName?: string;
    lastName?: string;
}