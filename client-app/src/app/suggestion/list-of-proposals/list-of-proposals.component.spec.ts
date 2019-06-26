import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListOfProposalsComponent } from './list-of-proposals.component';

describe('ListOfProposalsComponent', () => {
  let component: ListOfProposalsComponent;
  let fixture: ComponentFixture<ListOfProposalsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListOfProposalsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListOfProposalsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
