import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnovationsComponent } from './innovations';

describe('Innovations', () => {
  let component: InnovationsComponent;
  let fixture: ComponentFixture<InnovationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InnovationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InnovationsComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
