const mod = require('./util');
const activeAreaPath = mod.activeAreaPath;
const toggleClass = mod.toggleClass;


describe('Add class is-active', () => {
  const mockedElementDOM = { classList: { contains: jest.fn(), remove: jest.fn(), add: jest.fn() } };
  it('should add the class is-active', () => {
    activeAreaPath(mockedElementDOM);
    expect(mockedElementDOM.classList.add).toBeCalledWith('is-active');
  });
});

describe('Add class', () => {
  const mockedElementDOM = { classList: { contains: jest.fn(), remove: jest.fn(), add: jest.fn() } };

  it('should remove the class', () => {
    const className = 'a';
    mockedElementDOM.classList.contains.mockReturnValueOnce(true);
    toggleClass(mockedElementDOM, className);
    expect(mockedElementDOM.classList.remove).toBeCalledWith('a');
  });

  it('should add the class', () => {
    const className = 'a';
    mockedElementDOM.classList.contains.mockReturnValueOnce(false);
    toggleClass(mockedElementDOM, className);
    expect(mockedElementDOM.classList.add).toBeCalledWith('a');
  });
});