import React from 'react';
import {IGroup} from "../../../models/groups";
import InfiniteScroll from "react-infinite-scroll-component";
import {List} from "semantic-ui-react";
import {GroupListItem} from "../GroupListItem";
import "./GroupList.scss";

export interface GroupsListProps {
    groups: IGroup[]
    hasMore: boolean;
    loadMore: () => void;
}

export const GroupsList = ({groups, hasMore, loadMore}: GroupsListProps) => {
    return (
        <InfiniteScroll
            next={loadMore}
            hasMore={hasMore}
            loader={<h4>Loading...</h4>}
            dataLength={groups.length}
        >
            <List relaxed="very" divided>
                {groups.length
                    ? groups.map((group) => (
                        <GroupListItem group={group} key={group.id}/>
                    ))
                    : <List.Item className="no-group-found">Вы пока не состоите ни в одной группе.</List.Item>
                }
            </List>
        </InfiniteScroll>
    );
};