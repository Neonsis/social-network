import React from "react";
import {IComment} from "../../../models/post";
import {Comment} from "semantic-ui-react";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import {Link} from "react-router-dom";
import {parseDate} from "../../../util/util";
import {cropImage} from "../../../util/image";

export interface CommentsListProps {
    comments: IComment[];
}

export const CommentsList = ({comments}: CommentsListProps) => {
    return (
        <Comment.Group>
            {comments.map((comment => (
                <Comment key={comment.id}>
                    <Comment.Avatar src={comment.profile.avatar ? cropImage(comment.profile.avatar.originalUrl) : AvatarNotFound}/>
                    <Comment.Content>
                        <Comment.Author
                            as={Link}
                            to={`/id${comment.profile.id}`}
                        >
                            {comment.profile.firstName} {comment.profile.lastName}
                        </Comment.Author>
                        <Comment.Metadata>
                            <div>{parseDate(new Date(comment.createdAt))}</div>
                        </Comment.Metadata>
                        <Comment.Text>{comment.content}</Comment.Text>
                    </Comment.Content>
                </Comment>
            )))}
        </Comment.Group>
    );
};