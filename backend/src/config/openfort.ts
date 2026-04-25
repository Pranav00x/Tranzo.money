import Openfort from "@openfort/openfort-node";
import { ENV } from "./env.js";

// Initialize Openfort if API key is provided, otherwise null
// Migration to ZeroDev in progress
export const openfort = ENV.OPENFORT_API_KEY
  ? new Openfort(ENV.OPENFORT_API_KEY)
  : null;
