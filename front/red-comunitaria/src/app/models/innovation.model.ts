import { Location } from './location.model';
import { Person } from './person.model';
import { User } from './user.model';

export interface Innovation {
  id?: number;
  name: string;
  innovationType: string;
  innovationLevel: string;
  innovationDate?: string;
  impactScore?: number;
  description?: string;
  person: Person;
  location: Location;
  user: User;
  createdAt?: string;
  updatedAt?: string;
}
