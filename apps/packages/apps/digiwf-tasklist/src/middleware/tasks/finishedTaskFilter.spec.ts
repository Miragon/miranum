import {addFinishedTaskIds, FINISHED_TASK_IDS_KEY, getFinishedTaskIds} from "./finishedTaskFilter";
import {HumanTask} from "./tasksModels";

/*
 source: https://stackoverflow.com/questions/51566816/what-is-the-best-way-to-mock-window-sessionstorage-in-jest
 */
const localStorageMock = (() => {
  let store: any = {};

  return {
    getItem(key: string) {
      return store[key] || null;
    },
    setItem(key: string, value: string) {
      store[key] = value.toString();
    },
    removeItem(key: string) {
      delete store[key];
    },
    clear() {
      store = {};
    }
  };
})();
Object.defineProperty(window, 'sessionStorage', {
  value: localStorageMock
});


describe("finishedTaskFilter", () => {
  beforeEach(() => {
    window.sessionStorage.clear();
    jest.restoreAllMocks();
  });

  describe("addFinishedTaskIds", () => {
    it("should add id correctly", () => {
      localStorageMock.setItem(FINISHED_TASK_IDS_KEY, '["a","b"]')
      addFinishedTaskIds("c");
      expect(localStorageMock.getItem(FINISHED_TASK_IDS_KEY)).toBe('["a","b","c"]')
    });
  });

  describe("getFinishedTaskIds", () => {
    it("should return parsed array from sessionStorage", () => {
      localStorageMock.setItem(FINISHED_TASK_IDS_KEY, '["a","b"]');
      expect(getFinishedTaskIds()).toEqual(["a", "b"]);
    })
    it("should return empty array if no value exists in sessionStorage", () => {
      expect(getFinishedTaskIds()).toEqual([]);
    })
    it("should return empty array if json is not parsable", () => {
      localStorageMock.setItem(FINISHED_TASK_IDS_KEY, 'no-json');
      expect(getFinishedTaskIds()).toEqual([]);
    })
  })
});


const createDummyTask = (id: string): HumanTask => ({
  id,
  assigneeFormatted: "assigneeFormatted",
  assigneeId: "assigneeId",
  followUpDate: undefined,
  createTime:"createTime",
  name: `Task ${id}`,
  processName: "processName",
  description: "description",
  inFinishProcess: false,
})
