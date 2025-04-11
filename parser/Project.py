from dataclasses import dataclass


@dataclass
class Project:
    projectType: str
    projectRole: str
    projectIndustrySector: str
    projectDetails: []