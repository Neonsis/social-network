export interface IProfileDetails {
    birthday: Date;
    gender: Gender;
    about?: string;
    country?: string;
    city?: string;
}

export enum Gender {
    MALE = "MALE", FEMALE = "FEMALE"
}
