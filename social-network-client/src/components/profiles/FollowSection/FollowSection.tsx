import React from 'react';
import {Header, Segment} from "semantic-ui-react";
import {IGroup} from "../../../models/groups";
import {Page} from "../../../models/page";
import {GroupAvatar} from "../../groups/GroupAvatar";
import "./FollowSection.scss";

export interface FollowSectionProps {
    groups: Page<IGroup[]>;
    loading: boolean;
}

export const FollowSection = ({groups, loading}: FollowSectionProps) => {

    if (loading || !groups) return null;

    return (
        <Segment className="follow__section">
            <Header>Подписки {groups.totalElements}</Header>
            {groups.content.map(group => (
                <GroupAvatar key={group.id} group={group}/>
            ))}
        </Segment>
    );
};