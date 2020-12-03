import React from "react";
import {Card, Divider, Icon, Image} from "semantic-ui-react";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import "./Post.scss";
import {ICommentFormValues, IPost} from "../../../models/post";
import {Link} from "react-router-dom";
import {observer} from "mobx-react-lite";
import {CommentsSection} from "../../comments/CommentsSection";
import {parseDate} from "../../../util/util";
import {cropImage} from "../../../util/image";

export interface PostProps {
    post: IPost;
    like: (postId: string) => void;
    unlike: (postId: string) => void;
    loggedInUserId: string;
    onDelete: (postId: string) => void;
    addComment: (postId: string, values: ICommentFormValues) => void;
}

export const Post = observer(({post, like, unlike, loggedInUserId, onDelete, addComment}: PostProps) => {
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
                    src={author.avatar ? cropImage(author.avatar.originalUrl) : AvatarNotFound}
                />
                <Card.Header
                    className="post__author-name"
                    as={Link}
                    to={`/id${author.id}`}
                >
                    {author.firstName} {author.lastName}
                </Card.Header>
                {post.author.id === loggedInUserId &&
                <Icon name="delete" className="post-delete" onClick={() => onDelete(post.id)}/>}
                <Card.Meta className="post__date">{parseDate(new Date(createdAt))}</Card.Meta>
                <Card.Description>
                    <pre className="post-content">
                    {content}
                    </pre>
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
                <CommentsSection postId={id} comments={comments} addComment={addComment}/>
            </Card.Content>
        </Card>
    );
});