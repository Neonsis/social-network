import React, {useEffect, useState} from 'react';
import {Grid, Input, Menu, Segment} from "semantic-ui-react";
import {Link} from "react-router-dom";
import "./GroupFindPage.scss";
import {RouteComponentProps} from "react-router";

export const GroupFindPage = ({location}: RouteComponentProps) => {

    const urlSearchParams = new URLSearchParams(location.search);

    const isAdminTab = urlSearchParams.get("tab") === "admin";
    const isPopularTab = urlSearchParams.get("act") === "popular";

    const [searchValue, setSearchValue] = useState("");

    useEffect(() => {
        setSearchValue("");
    }, [isAdminTab, isPopularTab])

    return (
        <Grid className="group-page">
            <Grid.Column width={11}>
                <Segment>
                    <Menu pointing secondary>
                        <Menu.Item
                            as={Link}
                            name="Все сообщества"
                            active={!isAdminTab}
                        />
                        <Menu.Item
                            as={Link}
                            to={"/groups?tab=admin"}
                            name="Управление"
                            active={isAdminTab}
                        />
                    </Menu>
                    <Input
                        fluid
                        icon="search"
                        iconPosition="left"
                        placeholder="Поиск сообществ"
                        transparent
                        className="group-search"
                        value={searchValue}
                        onChange={((event, data) => setSearchValue(data.value))}
                    />
                </Segment>
            </Grid.Column>
            <Grid.Column width={5} className="group-menu">
                <Menu vertical>
                    <Menu.Item
                        name="friends"
                        as={Link}
                        to="/groups"
                        active={!isPopularTab}
                    >
                        Мои сообщества
                    </Menu.Item>
                    <Menu.Item
                        name="friends"
                        as={Link}
                        to="/groups?act=popular"
                        active={isPopularTab}
                    >
                        Популярные сообщества
                    </Menu.Item>
                </Menu>
            </Grid.Column>
        </Grid>
    );
};