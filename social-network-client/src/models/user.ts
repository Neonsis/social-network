export interface IUser {
    firstName: string;
    lastName: string;
    token: string;
    avatarUrl?: string;
}

export interface IUserFormValues {
    email: string;
    password: string;
    firstName?: string;
    lastName?: string;
}