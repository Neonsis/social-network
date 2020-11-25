import React from 'react';
import {Feed} from "semantic-ui-react";
import {IMessage} from "../../../models/chat";
import {Link} from "react-router-dom";
import {parseDate} from "../../../util/util";
import "./ChatMessage.scss";
import AvatarNotFound from "../../../assets/avatar_not_found.png";

export interface ChatMessageProps {
    message: IMessage;
}

export const ChatMessage = ({message}: ChatMessageProps) => {
    return (
        <Feed>
            <Feed.Event className="message">
                <Feed.Label>
                    <img src={message.sender.avatarUrl ? message.sender.avatarUrl : AvatarNotFound} alt="Avatar"/>
                </Feed.Label>
                <Feed.Content>
                    <Feed.Summary>
                        <Feed.User as={Link}
                                   to={`/id${message.sender.id}`}>{message.sender.firstName} {message.sender.lastName}
                        </Feed.User>
                        <Feed.Date>{parseDate(new Date(message.createdAt))}</Feed.Date>
                    </Feed.Summary>
                    <Feed.Extra text>
                        {message.content}
                    </Feed.Extra>
                </Feed.Content>
            </Feed.Event>
        </Feed>
    );
};