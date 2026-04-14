import Openfort from "@openfort/openfort-node";
import { ENV } from "./env.js";

export const openfort = new Openfort(ENV.OPENFORT_API_KEY);
