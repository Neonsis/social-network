export interface Page<T> {
    content: T;
    totalPages: number;
    totalElements: number;
    last: boolean;
    number: number;
    size: number;
}