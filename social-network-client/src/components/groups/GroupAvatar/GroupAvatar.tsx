import React from 'react';
import {Link} from "react-router-dom";
import {Image} from "semantic-ui-react";
import {cropImage} from "../../../util/image";
import GroupNotFound from "../../../assets/group_not_found.png";
import {IGroup} from "../../../models/groups";
import "./GroupAvatar.scss";

export interface GroupAvatarProps {
    group: IGroup;
}

export const GroupAvatar = ({group}: GroupAvatarProps) => {
    return (
        <div className="group__section-item">
            <Link className="group__item-avatar-wrapper" to={`/group${group.id}`}>
                <Image className="group__item-avatar"
                       avatar
                       src={group.avatar ? cropImage(group.avatar.originalUrl) : GroupNotFound}
                />
                <span className="group__header">{group.title}</span>
            </Link>
        </div>
    );
};