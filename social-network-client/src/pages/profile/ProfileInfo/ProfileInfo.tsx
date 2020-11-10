import React, {useContext, useEffect} from 'react';
import "./ProfileInfo.scss";
import {Header, Segment} from "semantic-ui-react";
import Divider from "semantic-ui-react/dist/commonjs/elements/Divider";
import {RootStoreContext} from "../../../stores/rootStore";
import {observer} from "mobx-react-lite";
import {LoadingComponent} from "../../../components/layot/LoadingComponent";

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
    firstName: string;
    lastName: string;
    id: string;
}


export const ProfileInfo = observer(({firstName, lastName, id}: ProfileInfoProps) => {
    const rootStore = useContext(RootStoreContext);
    const {loadingProfileDetails, profileDetails, loadProfileDetails} = rootStore.profileStore;

    const parseField = (field: IProfileField): string => {
        if (field.contentField === "birthday") {
            const options = {year: 'numeric', month: 'long', day: 'numeric'};
            return new Date(profileDetails!.birthday).toLocaleDateString("ru-RU", options);
        }
        return profileDetails![field.contentField]!;
    }

    useEffect(() => {
        loadProfileDetails(id);
    }, [id, loadProfileDetails])

    if (loadingProfileDetails) return <LoadingComponent content="Loading app..."/>

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