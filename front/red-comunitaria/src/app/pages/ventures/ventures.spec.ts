import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VenturesComponent } from './ventures';

describe('Ventures', () => {
  let component: VenturesComponent;
  let fixture: ComponentFixture<VenturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VenturesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VenturesComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
