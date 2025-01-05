import { appRoutes, appExternalRoutes } from '../routes';

// const mixinRoutes = [...appRoutes, ...appExternalRoutes];

const mixinRoutes = [];

// @ts-ignore
const appClientMenus = mixinRoutes
  .filter((x) => !x.hidden)
  .map((el) => {
    const { name, path, meta, redirect, children } = el;
    return {
      name,
      path,
      meta,
      redirect,
      // @ts-ignore
      children: children?.filter((x) => !x.hidden),
    };
  });

export default appClientMenus;
