import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlatesViewComponent } from './plates-view.component';

describe('PlatesViewComponent', () => {
  let component: PlatesViewComponent;
  let fixture: ComponentFixture<PlatesViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlatesViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlatesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
