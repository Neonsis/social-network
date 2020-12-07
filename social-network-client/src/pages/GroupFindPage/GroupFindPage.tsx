import React, {useContext, useEffect, useState} from 'react';
import {Button, Form, Grid, Header, Image, Input, Menu, Modal, Segment} from "semantic-ui-react";
import {Link} from "react-router-dom";
import "./GroupFindPage.scss";
import {RouteComponentProps} from "react-router";
import {GroupsList} from "../../components/groups/GroupsList";
import {RootStoreContext} from "../../stores/rootStore";
import {observer} from "mobx-react-lite";
import ImageGroup from "../../assets/groups_create.png";

export const GroupFindPage = observer(({location, history}: RouteComponentProps) => {
    const rootStore = useContext(RootStoreContext);
    const {
        userGroups,
        fetchMoreUserGroups,
        isLastGroups,
        loadUserGroups,
        loadModeratorGroups,
        loadPopularGroups,
        creatGroup,
        createGroupLoader
    } = rootStore.groupsStore;

    const urlSearchParams = new URLSearchParams(location.search);

    const isAdminTab = urlSearchParams.get("tab") === "admin";
    const isPopularTab = urlSearchParams.get("act") === "popular";

    const [searchValue, setSearchValue] = useState("");
    const [open, setOpen] = useState(false)
    const [title, setTitle] = useState("");

    useEffect(() => {
        setSearchValue("");
        if (isAdminTab) {
            loadModeratorGroups(searchValue);
        } else {
            if (isPopularTab) {
                loadPopularGroups(searchValue);
            } else {
                loadUserGroups(searchValue);
            }
        }
    }, [isAdminTab, isPopularTab])

    useEffect(() => {
        if (isAdminTab) {
            loadModeratorGroups(searchValue);
        } else {
            if (isPopularTab) {
                loadPopularGroups(searchValue);
            } else {
                loadUserGroups(searchValue);
            }
        }
    }, [searchValue])

    const handleSubmit = async () => {
        await creatGroup(title);
    }

    return (
        <Grid className="group-page">
            <Grid.Column width={11}>
                <Segment>
                    <Menu pointing secondary>
                        <Menu.Item
                            as={Link}
                            to="/groups"
                            name="Все сообщества"
                            active={!isAdminTab}
                        />
                        <Menu.Item
                            as={Link}
                            to="/groups?tab=admin"
                            name="Управление"
                            active={isAdminTab}
                        />
                        <Modal
                            onClose={() => setOpen(false)}
                            onOpen={() => setOpen(true)}
                            open={open}
                            trigger={
                                <Button
                                    className="group__button primary-button"
                                >
                                    Создать сообщество
                                </Button>
                            }
                        >
                            <Modal.Header>Сообщество ВКонтакте</Modal.Header>
                            <Modal.Content image>
                                <Image size='medium' src={ImageGroup} wrapped/>
                                <Modal.Description>
                                    <Header>Создание сообщества</Header>
                                    <Form>
                                        <Form.Field inline>
                                            <label>Название:</label>
                                            <input
                                                name="name"
                                                placeholder="Название"
                                                onChange={event => setTitle(event.currentTarget.value)}
                                            />
                                        </Form.Field>
                                    </Form>
                                </Modal.Description>
                            </Modal.Content>
                            <Modal.Actions>
                                <Button
                                    className="exit-button"
                                    onClick={() => setOpen(false)}
                                >
                                    Выйти
                                </Button>
                                <Button
                                    className="create-group-button primary-button"
                                    onClick={handleSubmit}
                                    loading={createGroupLoader}
                                >
                                    Создать сообщество
                                </Button>
                            </Modal.Actions>
                        </Modal>
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
                    <GroupsList
                        groups={userGroups}
                        hasMore={!isLastGroups}
                        loadMore={() => fetchMoreUserGroups(searchValue)}
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
    )
});