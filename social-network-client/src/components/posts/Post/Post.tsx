import React from 'react';
import {Card, Divider, Icon, Image} from "semantic-ui-react";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import "./Post.scss";
import {IPost} from "../../../models/post";
import {Link} from "react-router-dom";
import {observer} from "mobx-react-lite";

export interface PostProps {
    post: IPost;
    like: (postId: string) => void;
    unlike: (postId: string) => void;
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

export const Post = observer(({post, like, unlike}: PostProps) => {
    const {
        content,
        author,
        createdAt,
        id,
        isLiked,
        countLike
    } = post;

    const handleLike = () => {
        like(id);
    }

    const handleUnlike = () => {
        unlike(id);
    }

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
                <Divider/>
                <Card.Content extra>
                    <a onClick={isLiked ? handleUnlike : handleLike}>
                        <Icon className={isLiked ? "liked" : ""} name="like" size="large"/>
                        {countLike}
                    </a>
                </Card.Content>
            </Card.Content>
        </Card>
    );
});