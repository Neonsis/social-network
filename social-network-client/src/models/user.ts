export interface IUser {
    id: string;
    firstName: string;
    lastName: string;
    token: string;
    avatarUrl?: string;
}

export interface IUserDetails {
    id: string;
    firstName: string;
    lastName: string;
    avatarUrl: string;
    email: string;
    isFriend: boolean;
    isLoggedInUser: boolean;
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