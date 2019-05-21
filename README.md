# Advanced Jenkins config as code setup

Creating Jenkins configation as code and applying changes without downtime with Groovy, Java, Docker and Jenkins job.

## Getting started

1. Clone the repo

2. Install pre-commit

`$ pip install pre-commit`

3. Install pre-commit hooks

`$ pre-commit install`

## How to add a new node for a new team

We organize our slave nodes by squad team, we don't have an algorithm to auto detect a new team so far, we have add these team manually, here's the steps:

1. Add team's `podTemplates` to `./config/kubernetes.ctmpl`

2. Add new temptlate file named with `node-<team's name>` to folder `./config/`

3. Add new node's `Dockerfile` into `./dockerfiles/`

4. Add templating config into `./jenkins_config.hcl`

5. Create a new PR for that for SRE team review

NOTE: VERY IMPORTANT, Make sure you have tested the PR on test Jenkins, once it's merged, it will be applied automatically into Stable Jenkins.

## TODO

- Make common part of dockerfiles a base Dockerfile

- Auto detect new team
