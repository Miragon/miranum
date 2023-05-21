import Vue from "vue";
import Router from "vue-router";
import Tasks from "@/views/Tasks.vue";
import TaskDetail from "@/views/TaskDetail.vue";
import Processes from "@/views/Processes.vue";
import StartProcess from "@/views/StartProcess.vue";
import ProcessInstances from "@/views/ProcessInstances.vue";
import OpenGroupTasks from "@/views/OpenGroupTasks.vue";
import AssignedGroupTasks from "@/views/AssignedGroupTasks.vue";
import GroupTaskDetail from "@/views/GroupTaskDetail.vue";
import ProcessInstanceDetailView from "@/views/ProcessInstanceDetailView.vue";
import store from "./store";
import {baseUrl} from "./utils/envVariables";

Vue.use(Router);

/*
* Preventing "NavigationDuplicated" errors in console in Vue-router >= 3.1.0
* https://github.com/vuejs/vue-router/issues/2881#issuecomment-520554378
* */
/* eslint-disable @typescript-eslint/no-explicit-any */
const routerMethods = ['push', 'replace'];
routerMethods.forEach((method: string) => {
  const originalCall = (Router.prototype as any)[method];
  (Router.prototype as any)[method] = function (location: any, onResolve: any, onReject: any): Promise<any> {
    if (onResolve || onReject) {
      return originalCall.call(this, location, onResolve, onReject);
    }
    return (originalCall.call(this, location) as any).catch((err: any) => err);
  };
});
/* eslint-enable @typescript-eslint/no-explicit-any */

const router = new Router({
  base: baseUrl,
  routes: [
    {
      path: "/mytask",
      name: "mytask",
      component: Tasks
    },
    {
      path: '/task/:id',
      component: TaskDetail,
      props: true
    },
    {
      path: "/process",
      name: "processes",
      component: Processes
    },
    {
      path: '/process/:processKey',
      component: StartProcess,
      props: true
    },
    {
      path: '/instance',
      component: ProcessInstances,
      props: true

    },
    {
      path: '/instance/:processId',
      component: ProcessInstanceDetailView,
      props: true
    },
    {
      path: '/opengrouptask',
      component: OpenGroupTasks
    },
    {
      path: '/opengrouptask/:id',
      component: GroupTaskDetail,
      props: true
    },
    {
      path: '/assignedgrouptask',
      component: AssignedGroupTasks
    },
    {
      path: '/assignedgrouptask/:id',
      component: GroupTaskDetail,
      props: true
    },
    {path: '*', redirect: '/mytask'} //Fallback 2
  ]
});

// hide menu if the the query parameter MenuSichtbar is set to false
router.beforeEach((to, from, next) => {
  if (to.query.MenuSichtbar) {
    const value = to.query.MenuSichtbar;
    delete to.query.MenuSichtbar;

    if (value === "Nein") {
      store.dispatch('menu/setOpen', false);
    } else {
      store.dispatch('menu/setOpen', true);
    }

    next({path: to.path, params: to.params, query: to.query});
  } else {
    next();
  }

});

export default router;
