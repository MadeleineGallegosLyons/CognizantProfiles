from dataclasses import dataclass
from datetime import datetime
from typing import List
import Project

@dataclass
class Profile:
    profileId: int
    consultantId: str
    consultantName: str
    profileRef: str
    creationDate: datetime
    role: str
    profileState: str
    profileName: str
    profileJobTitle: str
    profileExecutiveSummary: str
    profileEmail: str
    profileMobility: int
    profileLanguages: []
    profileMethodologies: []
    profileCertifications: []
    profileIndustrySectors: []
    profileTechnicalExpertise: []
    profileFunctionalExpertise: []
    profileExperience: List[Project]




