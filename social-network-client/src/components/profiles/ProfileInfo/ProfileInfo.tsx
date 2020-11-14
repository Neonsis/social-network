import React from "react";
import "./ProfileInfo.scss";
import {Header, Segment} from "semantic-ui-react";
import Divider from "semantic-ui-react/dist/commonjs/elements/Divider";
import {observer} from "mobx-react-lite";
import {IProfileDetails} from "../../../models/profile";
import ContentLoader from "react-content-loader";

export interface IProfileField {
    label: string;
    contentField: "birthday" | "country" | "city" | "about";
}

const profileFields: IProfileField[] = [
    {
        label: "День рождения",
        contentField: "birthday"
    },
    {
        label: "Страна",
        contentField: "country"
    },
    {
        label: "Город",
        contentField: "city"
    },
    {
        label: "О себе",
        contentField: "about"
    }
];

export type ProfileInfoProps = {
    firstName?: string;
    lastName?: string;
    profileDetails?: IProfileDetails;
    loading: boolean;
}


export const ProfileInfo = observer(({firstName, lastName, profileDetails, loading}: ProfileInfoProps) => {

    if (loading) return (
        <Segment className="profile__info">
            <ContentLoader
                speed={2}
                width={538}
                height={104}
                viewBox="0 0 538 104"
                backgroundColor="#f3ecec"
                foregroundColor="#ffffff"
            >
                <rect x="0" y="14" rx="0" ry="0" width="510" height="76" />
            </ContentLoader>
        </Segment>
    )

    const parseField = (field: IProfileField): string => {
        if (field.contentField === "birthday") {
            const options = {year: "numeric", month: "long", day: "numeric"};
            return new Date(profileDetails!.birthday).toLocaleDateString("ru-RU", options);
        }
        return profileDetails![field.contentField]!;
    }

    let profileInfo = profileFields
        .filter(field => profileDetails![field.contentField]) // if undefined skip
        .map((field) => (
            <div className="profile__info-item">
                <div className="profile__item-label">
                    {`${field.label}:`}
                </div>
                <div className="profile__item-content">
                    {parseField(field)}
                </div>
            </div>
        ));

    return (
        <Segment className="profile__info">
            <Header as="h3">
                <div className="profile__info-header">{`${firstName} ${lastName}`}</div>
                <Divider/>
                {profileInfo.length !== 0 ? profileInfo :
                    <div className="profile__info--absent">Информация отсутствует</div>}
            </Header>
        </Segment>
    );
});