import { Location } from './location.model';
import { User } from './user.model';

export interface Person {
  id?: number;
  firstName: string;
  lastName: string;
  birthDate?: string;
  gender?: string;
  educationLevel?: string;
  location: Location;
  user: User;
  createdAt?: string;
  updatedAt?: string;
}
