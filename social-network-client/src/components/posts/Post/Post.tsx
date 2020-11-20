import React from "react";
import {Card, Divider, Icon, Image} from "semantic-ui-react";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import "./Post.scss";
import {IPost} from "../../../models/post";
import {Link} from "react-router-dom";
import {observer} from "mobx-react-lite";
import {CommentsSection} from "../../comments/CommentsSection";
import {parseDate} from "../../../util/util";

export interface PostProps {
    post: IPost;
    like: (postId: string) => void;
    unlike: (postId: string) => void;
    userId: string;
    onDelete: (postId: string) => void;
}

export const Post = observer(({post, like, unlike, userId, onDelete}: PostProps) => {
    const {
        content,
        author,
        createdAt,
        id,
        isLiked,
        countLike,
        comments
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
                {post.author.id === userId && <Icon name="delete" className="post-delete" onClick={() => onDelete(post.id)}/>}
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
            <Card.Content extra>
                <CommentsSection postId={id} comments={comments}/>
            </Card.Content>
        </Card>
    );
});