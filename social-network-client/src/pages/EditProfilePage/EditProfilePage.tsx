import React, {ChangeEvent, SyntheticEvent, useContext, useEffect} from "react";
import "./EditProfilePage.scss";
import {RootStoreContext} from "../../stores/rootStore";
import {Button, DropdownProps, Form, Grid, Header, Message, Segment, Select} from "semantic-ui-react";
import {useForm} from "react-hook-form";
import {Gender, IProfileDetails} from "../../models/profile";
import SemanticDatepicker from "react-semantic-ui-datepickers";
import {observer} from "mobx-react-lite";
import {SemanticDatepickerProps} from "react-semantic-ui-datepickers/dist/types";
import ContentLoader from "react-content-loader";

const options = [
    {key: "f", text: "Male", value: Gender.FEMALE.toLocaleString()},
    {key: "m", text: "Female", value: Gender.MALE.toLocaleString()}
]

export const EditProfilePage = observer(() => {
    const rootStore = useContext(RootStoreContext);
    const {
        user
    } = rootStore.userStore;
    const {
        profileDetails,
        loadProfileDetails,
        saveProfileDetails,
        loadingProfileDetails,
        loadingSaveProfileDetails,
        successUpdated
    } = rootStore.profileStore;
    const {register, handleSubmit, setValue, errors} = useForm<IProfileDetails>();

    useEffect(() => {
        loadProfileDetails(user!.id);
        register({name: "about"}, {});
        register({name: "birthday"}, {required: true});
        register({name: "gender"}, {required: true});
    }, []);

    const onSubmit = (values: IProfileDetails) => {
        if (!values.country) {
            values.country = undefined;
        }
        if (!values.city) {
            values.city = undefined;
        }
        saveProfileDetails(values);
    }

    const onBirthdayChange = async (event: SyntheticEvent<Element, Event> | undefined, data: SemanticDatepickerProps) => {
        setValue("birthday", data.value);
    }

    const onGenderChange = async (e: SyntheticEvent<HTMLElement>, {name, value}: DropdownProps) => {
        setValue(name, value);
    }

    const onAboutChange = async (e: ChangeEvent<HTMLTextAreaElement>) => {
        setValue("about", e.currentTarget.value);
    }


    if (loadingProfileDetails || !profileDetails) return (
        <Grid className="edit-page">
            <Grid.Column width={11}>
                <Segment>
                    <Header as="h3">
                        Основное
                    </Header>
                    <ContentLoader
                        speed={2}
                        width={507.81}
                        height={421.63}
                        viewBox="0 0 507.81 421.63"
                        backgroundColor="#f3f3f3"
                        foregroundColor="#ecebeb"
                    >
                        <rect x="48" y="26" rx="3" ry="3" width="52" height="6"/>
                        <rect x="0" y="0" rx="0" ry="0" width="508" height="422"/>
                    </ContentLoader>
                </Segment>
            </Grid.Column>
        </Grid>
    )

    setValue("birthday", profileDetails.birthday);
    setValue("gender", profileDetails.gender);
    setValue("about", profileDetails.about);
    setValue("city", profileDetails.city);
    setValue("country", profileDetails.country);

    return (
        <Grid className="edit-page">
            <Grid.Column width={11}>
                <Segment>
                    <Header as="h3">
                        Основное
                    </Header>
                    {successUpdated && <Message positive>
                        <Message.Header>Изменения сохранены</Message.Header>
                        Новые данные будут отражены на Вашей странице.
                    </Message>}
                    <Form onSubmit={handleSubmit(onSubmit)}>
                        <Form.Field inline>
                            <label>Страна:</label>
                            <input
                                defaultValue={profileDetails.country}
                                name="country"
                                placeholder="Country"
                                ref={register()}
                            />
                        </Form.Field>
                        <Form.Field inline>
                            <label>Город:</label>
                            <input
                                defaultValue={profileDetails.city}
                                name="city"
                                placeholder="City"
                                ref={register()}
                            />
                        </Form.Field>
                        <Form.Field inline>
                            <label>Пол:</label>
                            <Select
                                name="gender"
                                options={options}
                                placeholder="Gender"
                                defaultValue={profileDetails.gender}
                                onChange={onGenderChange}
                            />
                        </Form.Field>
                        <Form.Field inline error={errors.birthday}>
                            <label>День рождения:</label>
                            <SemanticDatepicker
                                value={profileDetails.birthday && new Date(profileDetails.birthday)}
                                name="birthday"
                                onChange={onBirthdayChange}
                                error={errors.birthday}
                            />
                        </Form.Field>
                        <Form.Field inline>
                            <label>О себе:</label>
                            <textarea
                                defaultValue={profileDetails.about}
                                name="about"
                                className="text-area"
                                placeholder="Tell us more about you..."
                                onChange={onAboutChange}
                            />
                        </Form.Field>
                        <div className="button">
                            <Button
                                type="submit"
                                className="primary-button"
                                disabled={loadingSaveProfileDetails}
                                loading={loadingSaveProfileDetails}
                            >
                                Сохранить
                            </Button>
                        </div>
                    </Form>
                </Segment>
            </Grid.Column>
        </Grid>
    );
});