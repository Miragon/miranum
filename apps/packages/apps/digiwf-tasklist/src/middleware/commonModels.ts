export interface Page<T> {
  readonly content: T[];
  readonly totalElements?: number;
  readonly totalPages: number;
  readonly page: number,
  readonly size: number
}
