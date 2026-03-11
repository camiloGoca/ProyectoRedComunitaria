import { Location } from './location.model';
import { Person } from './person.model';
import { User } from './user.model';

export interface Venture {
  id?: number;
  name: string;
  ventureType: string;
  sector?: string;
  startDate?: string;
  status?: string;
  employees?: number;
  totalProduction?: number;
  monthlyIncome?: number;
  person: Person;
  location: Location;
  user: User;
  createdAt?: string;
  updatedAt?: string;
}
