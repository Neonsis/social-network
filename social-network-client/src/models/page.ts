export interface Page<T> {
    content: T;
    totalPages: number;
    totalElements: number;
    isLast: boolean;
    number: number;
    size: number;
}