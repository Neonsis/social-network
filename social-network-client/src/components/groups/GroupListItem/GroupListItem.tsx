import React from 'react';
import "./GroupListItem.scss";
import {IGroup} from "../../../models/groups";
import {Image, List} from "semantic-ui-react";
import {cropImage} from "../../../util/image";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import {Link} from "react-router-dom";

export interface GroupListItemProps {
    group: IGroup;
}

export const GroupListItem = ({group}: GroupListItemProps) => {
    return (
        <List.Item className="groups-list__item">
            <Image avatar size="tiny" src={group.avatar ? cropImage(group.avatar.originalUrl) : AvatarNotFound}/>
            <List.Content>
                <List.Header as={Link} to={`/group${group.id}`}>{group.title}</List.Header>
                <List.Description>
                    <List.Item className="write-message">
                        {group.followersCount} подписчиков
                    </List.Item>
                </List.Description>
            </List.Content>
        </List.Item>
    );
};