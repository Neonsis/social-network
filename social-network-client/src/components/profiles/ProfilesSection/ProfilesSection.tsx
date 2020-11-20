import React from "react";
import "./ProfilesSection.scss";
import {Header, Segment} from "semantic-ui-react";
import {IUser} from "../../../models/user";
import {UserAvatar} from "../../display/UserAvatar";
import {Page} from "../../../models/page";
import ContentLoader from "react-content-loader";

export interface ProfilesSectionProps {
    profiles: Page<IUser[]> | null;
    header: string;
    loading: boolean;
}

export const ProfilesSection = ({header, profiles, loading}: ProfilesSectionProps) => {

    if (loading || !profiles) return (
        <Segment className="profile__section">
            <Header as="h5">{header}</Header>
            <div className="profile__section-list">
                <ContentLoader
                    speed={2}
                    width={199}
                    height={100}
                    viewBox="0 0 199 100"
                    backgroundColor="#f3ecec"
                    foregroundColor="#ffffff"
                >
                    <circle cx="40" cy="23" r="23"/>
                    <circle cx="101" cy="23" r="23"/>
                    <circle cx="164" cy="23" r="23"/>
                    <circle cx="40" cy="76" r="23"/>
                    <circle cx="101" cy="76" r="23"/>
                    <circle cx="164" cy="76" r="23"/>
                </ContentLoader>
            </div>
        </Segment>
    )

    if (profiles.size === 0) return <React.Fragment/>;

    return (
        <Segment className="profile__section">
            <Header as="h5">{header} {profiles.totalElements}</Header>
            <div className="profile__section-list">
                {profiles && profiles.content.map(profile => (
                    <UserAvatar key={profile.id} user={profile}/>
                ))}
            </div>
        </Segment>
    );
};