import React from 'react';
import {Card, Image} from "semantic-ui-react";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import "./Post.scss";
import {IPost} from "../../../models/post";
import {Link} from "react-router-dom";

export interface PostProps {
    post: IPost;
}

const parseDate = (date: Date): string => {
    if (new Date().toLocaleDateString() === date.toLocaleDateString()) {
        // Today
        const time = date.toLocaleTimeString("ru-RU", {
            hour: '2-digit',
            minute: '2-digit'
        });
        return "сегодня в " + time;
    } else {
        return date.toLocaleTimeString("ru-RU", {
            day: "2-digit",
            month: "short",
            hour: '2-digit',
            minute: '2-digit'
        });
    }
}

export const Post = ({post}: PostProps) => {

    const {
        content,
        author,
        createdAt
    } = post;

    return (
        <Card className="post" fluid>
            <Card.Content>
                <Image
                    floated="left"
                    size="huge"
                    avatar
                    className="post__avatar"
                    src={author.avatarUrl ? author.avatarUrl : AvatarNotFound}
                />
                <Card.Header
                    className="post__author-name"
                    as={Link}
                    to={`/id${author.id}`}
                >
                    {author.firstName} {author.lastName}
                </Card.Header>
                <Card.Meta className="post__date">{parseDate(new Date(createdAt))}</Card.Meta>
                <Card.Description>
                    {content}
                </Card.Description>
            </Card.Content>
        </Card>
    );
};